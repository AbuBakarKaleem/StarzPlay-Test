package com.starzplay.entertainment.ui.fragments.player

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.starzplay.entertainment.databinding.FragmentPlayerBinding
import com.starzplay.entertainment.ui.base.BaseFragment
import com.starzplay.starzlibrary.helper.gone
import com.starzplay.starzlibrary.helper.show

class PlayerFragment : BaseFragment<FragmentPlayerBinding>(FragmentPlayerBinding::inflate) {

    private var player: ExoPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        requireActivity().requestedOrientation =
            android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializePlayer()
    }

    private fun initializePlayer() {
        player = ExoPlayer.Builder(requireContext()).build()
        val mediaUrl =
            "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"
        val mediaItem = MediaItem.fromUri(Uri.parse(mediaUrl))
        player?.setMediaItem(mediaItem)

        player?.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                when (playbackState) {
                    Player.STATE_BUFFERING -> {
                        binding.progressBar.show()
                    }

                    Player.STATE_READY -> {
                        binding.progressBar.gone()
                    }

                    Player.STATE_ENDED -> {
                        // do nothing
                    }

                    Player.STATE_IDLE -> {
                        //do nothing
                    }
                }
            }

            override fun onPlayerError(error: com.google.android.exoplayer2.PlaybackException) {
                binding.progressBar.gone()
                showAlertDialog(messages = error.message.toString(), onPosClick = { _, _ -> })
            }
        })

        player?.prepare()
        player?.playWhenReady = true
        binding.playerView.player = player
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    private fun releasePlayer() {
        player?.release()
        player = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Reset orientation when leaving this fragment
        requireActivity().requestedOrientation =
            android.content.pm.ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }
}