package app.revanced.patches.youtube.interaction.downloads.bytecode.patch

import app.revanced.patcher.annotation.Description
import app.revanced.patcher.annotation.Name
import app.revanced.patcher.annotation.Version
import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.PatchResult
import app.revanced.patcher.patch.PatchResultSuccess
import app.revanced.patcher.patch.annotations.DependsOn
import app.revanced.patcher.patch.annotations.Patch
import app.revanced.patches.youtube.interaction.downloads.annotation.DownloadsCompatibility
import app.revanced.patches.youtube.interaction.downloads.resource.patch.DownloadsResourcePatch
import app.revanced.patches.youtube.misc.playercontrols.bytecode.patch.PlayerControlsBytecodePatch
import app.revanced.patches.youtube.video.information.patch.VideoInformationPatch

@Patch
@Name("downloads")
@DependsOn([DownloadsResourcePatch::class, PlayerControlsBytecodePatch::class, VideoInformationPatch::class])
@Description("Adds a download button to the YouTube video player.")
@DownloadsCompatibility
@Version("0.0.1")
class DownloadsBytecodePatch : BytecodePatch() {
    private companion object {
        const val BUTTON_DESCRIPTOR = "Lapp/revanced/integrations/videoplayer/DownloadButton;"
    }

    override fun execute(context: BytecodeContext): PatchResult {
        /*
        initialize the control
         */

        PlayerControlsBytecodePatch.initializeControl(
            "$BUTTON_DESCRIPTOR->initializeButton(Landroid/view/View;)V")

        /*
         add code to change the visibility of the control
         */

        PlayerControlsBytecodePatch.injectVisibilityCheckCall(
            "$BUTTON_DESCRIPTOR->changeVisibility(Z)V")

        return PatchResultSuccess()
    }
}