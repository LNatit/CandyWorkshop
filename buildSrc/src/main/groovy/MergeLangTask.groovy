import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.*

/**
 * Merges Minecraft lang JSON files from multiple datagen output directories
 * into a single output file, using a priority-based strategy.
 *
 * <h3>Priority</h3>
 * Files are iterated in the order they were added via {@code sources.from(...)}.
 * Index 0 = highest priority: when the same key exists in multiple source files,
 * the value from the earliest-added source wins.
 *
 * <h3>Override / filter</h3>
 * The pre-execution content of {@code outputFile} acts as a manual-override
 * filter applied on top of the priority-merged result:
 * <ul>
 *   <li>Key in (merged ∩ output-file) → keep key, use <em>output-file</em>'s value</li>
 *   <li>Key in (merged \ output-file) → keep key, use merged (generated) value</li>
 *   <li>Key in (output-file \ merged) → <strong>discard</strong> (stale entry)</li>
 * </ul>
 */
abstract class MergeLangTask extends DefaultTask {
    MergeLangTask() {
        description = 'Priority-merges en_us.json from datagen dirs into src/main/resources, ' +
                      'using the existing main file as a manual-override filter.'
        group = 'build'
    }

    /**
     * Source lang JSON files to merge, in priority order.
     * Add files via {@code sources.from(...)}: first-added = highest priority.
     */
    @SkipWhenEmpty
    @InputFiles
    abstract ConfigurableFileCollection getSources()

    /**
     * The output file. Its pre-execution content is also read as a
     * manual-override filter before being overwritten with the merged result.
     */
    @OutputFile
    abstract RegularFileProperty getOutputFile()

    @TaskAction
    void merge() {
        def out = outputFile.asFile.get()

        // ① Priority merge ─────────────────────────────────────────────────
        // Reverse so that the first-added (highest-priority) source is applied
        // last via putAll and therefore overwrites lower-priority entries.
        def merged = sources.files.toList().reverse()
                .findAll { it.exists() }
                .inject([:] as Map) { acc, f -> acc + (new JsonSlurper().parse(f) as Map) }

        if (merged.isEmpty()) {
            logger.warn("[${name}] No source files found — skipping.")
            return
        }

        // ② Override / filter with pre-existing output file ────────────────
        def overrides = out.exists() ? (new JsonSlurper().parse(out) as Map) : [:]
        def result = merged.collectEntries { k, v -> [k, overrides.getOrDefault(k, v)] }

        // ③ Write — keys sorted alphabetically for reproducible diffs ──────
        out.parentFile.mkdirs()
        out.text = JsonOutput.prettyPrint(JsonOutput.toJson(result.sort()))
        logger.lifecycle("[${name}] Wrote ${result.size()} entries → ${out.path}")
    }
}



