package com.dash.projects.android.digiwave.`object`

import com.dash.projects.android.digiwave.R
import com.dash.projects.android.digiwave.model.SubjectMaterial

@Suppress("SpellCheckingInspection")
object SubjectMaterialData {
    private const val INDEX_DOES_NOT_MATCH =
        "SubjectMaterialData: array generated size is doesn't match"

    private const val BASE_URL = "https://drive.google.com"
    private const val FILE_PATH = "/file"
    private const val FOLDER_PATH = "/drive/folders"

    private const val MATERIAL_CONTRACT_URL =
        "${BASE_URL + FILE_PATH}/d/1HxZwxbXbokw_dLvwKKeTSJSKgP8sT8IA/view?usp=share_link"
    private const val MATERIAL_NUMSYS_URL =
        "${BASE_URL + FOLDER_PATH}/1jqGIrK5z0oL16MN2EygF4vITRAAXGJRv?usp=share_link"
    private const val MATERIAL_CODESYS_URL =
        "${BASE_URL + FOLDER_PATH}/1LAh0LtO14HVNg9Hq6A9q0PJYWM1lTs_I?usp=share_link"
    private const val MATERIAL_ELECTRONICS_COMPS_URL =
        "${BASE_URL + FOLDER_PATH}/1fOnbponD2rO9xm6DYBo72ICgfKlWSRJa?usp=share_link"
    private const val MATERIAL_LOGATE_URL =
        "${BASE_URL + FOLDER_PATH}/1SkiMcxby7El0PI7i06ejQP6kstKCRLs3?usp=share_link"
    private const val MATERIAL_DIGITAL_CIRCUITS_URL =
        "${BASE_URL + FOLDER_PATH}/1yjNt_oeMxtoWpmGRlwx7mXhxUGp8HSWK?usp=share_link"
    private const val MATERIAL_KMAP_URL =
        "${BASE_URL + FOLDER_PATH}/1Ilv_KvoXyu1sHgO_uUWD0ApFGkYyR5wd?usp=share_link"
    private const val MATERIAL_MID_EXAMS_URL =
        "${BASE_URL + FOLDER_PATH}/1X1p4_kgkdrwTfnh0CTr6uROP70CEa2qk?usp=share_link"
    private const val MATERIAL_ALGEBRA_BOOL_URL =
        "${BASE_URL + FOLDER_PATH}/1X6HCU4sV6qpLcJDu6A7QWiWkwzIeIRao?usp=share_link"
    private const val MATERIAL_SEVEN_SEGMENTS_URL =
        "${BASE_URL + FOLDER_PATH}/19UNpIwJ6yBcL6ZqIUk8jfk_A2kTnDx7u?usp=share_link"
    private const val MATERIAL_REGISTER_COUNTER_URL =
        "${BASE_URL + FOLDER_PATH}/1ALH3y-9MoJ2aGp_kgjkvU8c41CjtwIUm?usp=share_link"
    private const val MATERIAL_FLIP_FLOP_URL =
        "${BASE_URL + FOLDER_PATH}/118LIRHqI1pIhu4A807Xx0LR_zX0MhDXZ?usp=share_link"
    private const val MATERIAL_COMPARATOR_URL =
        "${BASE_URL + FOLDER_PATH}/1gLvQMCS19MEOUBc_uKrVARJBAa38eq4y?usp=share_link"
    private const val MATERIAL_DECODER_ENCODER_URL =
        "${BASE_URL + FOLDER_PATH}/1wIvacG0rrYwTjpECaQH4W_53Joqy4ZWB?usp=share_link"
    private const val MATERIAL_FINAL_EXAMS_URL =
        "${BASE_URL + FOLDER_PATH}/1qmU1TbWcFpUBhqPMT88lcUAxwTofsS0E?usp=share_link"

    private val subjectMaterialDescAndUrlLinks: List<Pair<Int, String>> = listOf(
        R.string.desc_subject_contract to MATERIAL_CONTRACT_URL,
        R.string.desc_subject_numsys to MATERIAL_NUMSYS_URL,
        R.string.desc_subject_codesys to MATERIAL_CODESYS_URL,
        R.string.desc_subject_electronics_comps to MATERIAL_ELECTRONICS_COMPS_URL,
        R.string.desc_subject_logate to MATERIAL_LOGATE_URL,
        R.string.desc_subject_digital_circuit to MATERIAL_DIGITAL_CIRCUITS_URL,
        R.string.desc_subject_kmap to MATERIAL_KMAP_URL,
        R.string.desc_subject_mid_exams to MATERIAL_MID_EXAMS_URL,
        R.string.desc_subject_bool_algebra to MATERIAL_ALGEBRA_BOOL_URL,
        R.string.desc_subject_seven_segments to MATERIAL_SEVEN_SEGMENTS_URL,
        R.string.desc_subject_register_counter to MATERIAL_REGISTER_COUNTER_URL,
        R.string.desc_subject_flip_flop to MATERIAL_FLIP_FLOP_URL,
        R.string.desc_subject_comparator_multiplexer to MATERIAL_COMPARATOR_URL,
        R.string.desc_subject_decoder_encoder to MATERIAL_DECODER_ENCODER_URL,
        R.string.desc_subject_final_exams to MATERIAL_FINAL_EXAMS_URL,
    )

    fun generateSubjectMaterials(): List<SubjectMaterial> {
        if (subjectMaterialDescAndUrlLinks.isEmpty()) return emptyList()
        if (subjectMaterialDescAndUrlLinks.size != 15)
            throw ArrayIndexOutOfBoundsException(INDEX_DOES_NOT_MATCH)

        return List(subjectMaterialDescAndUrlLinks.size) {
            val (subjectDescriptionResId, subjectUrlLink) = subjectMaterialDescAndUrlLinks[it]

            SubjectMaterial(
                R.drawable.ic_book,
                R.string.subject_material_title,
                subjectDescriptionResId,
                subjectUrlLink,
            )
        }
    }
}
