package com.example.homedesign.activities

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.homedesign.R
import com.google.ar.core.Anchor
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode

class ArActivity : BaseActivity() {

    private lateinit var arFragment: ArFragment

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_aractivity)

            // Load the ArFragment programmatically
            val arFragment = ArFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.arFragmentContainer, arFragment)
                .commit()
        }
    }

//    private fun placeObject(anchor: Anchor) {
//        ModelRenderable.builder()
//            .setSource(this, Uri.parse("chair.sfb"))  // Replace with your model file
//            .build()
//            .thenAccept { renderable ->
//                addNodeToScene(anchor, renderable)
//            }
//            .exceptionally {
//                Toast.makeText(this, "Error loading model", Toast.LENGTH_LONG).show()
//                null
//            }
//    }
//
//    private fun addNodeToScene(anchor: Anchor, renderable: ModelRenderable) {
//        val anchorNode = AnchorNode(anchor)
//        val transformableNode = TransformableNode(arFragment.transformationSystem)
//        transformableNode.setParent(anchorNode)
//        transformableNode.renderable = renderable
//        arFragment.arSceneView.scene.addChild(anchorNode)
//        transformableNode.select()
//    }
//}