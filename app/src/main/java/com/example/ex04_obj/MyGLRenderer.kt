package com.example.ex04_obj

import android.app.appsearch.SearchResult.MatchInfo
import android.content.Context
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.util.Log
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

//GLSurfaceView 를 랜더럴하는 클래스
class MyGLRenderer(mContext:Context): GLSurfaceView.Renderer {

	var mViewMatrix = FloatArray(16)
	var mProiMatrix = FloatArray(16)
	var modelMatrix = FloatArray(16)

	var andy: ObjRenderer
	var box: ObjRenderer
	var dog: ObjRenderer

	init {
		andy = ObjRenderer(mContext, "andy.obj", "andy.png")
		box = ObjRenderer(mContext, "crate.obj", "Crate_Base_Color.png")
		dog = ObjRenderer(mContext, "dog.obj", "dog.jpg")
	}


	override fun onSurfaceCreated(p0: GL10?, p1: EGLConfig?) {
		GLES30.glEnable(GLES30.GL_DEPTH_TEST)	// 3차원 입체감을 제공
		GLES30.glClearColor(1f, 0.6f, 0.6f, 1f)
		Log.d("MyGLRenderer 여", "onSurfaceCreated")
		
		andy.init()	// ObjRenderer 클래스안에있는 init함수임
		box.init()
		dog.init()
	}
	// 화면크기가 변경시 화면 크기를 가져와 작업
	override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
		Log.d("MyGLRenderer 여", "onSurfaceChanged")
		GLES30.glViewport(0, 0, width, height)

		var ratio = width.toFloat()/height

		// 프로젝션 Matrix 설정
		Matrix.frustumM(mProiMatrix, 0,
				-ratio, ratio,
			-1f,
			1f,
			20f,
			7000f
		)

//		Matrix.frustumM(mProiMatrix, 0,
//			-ratio, ratio,
//			-1f,
//			1f,
//			2f,
//			7f
//		)
	}

	override fun onDrawFrame(p0: GL10?) {
		Log.d("MyGLRenderer 여", "onDrawFrame")
		GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT or GLES30.GL_DEPTH_BUFFER_BIT)

		// 카메라 Matrix 설정
		Matrix.setLookAtM(mViewMatrix, 0,
			800f, 300f, 100f,
			0f, 0f, 0f,
			2f, 1f, 3f
		)

		Matrix.setIdentityM(modelMatrix, 0)


		andy.setModelMatrix(modelMatrix)
		andy.setViewMatrix(mViewMatrix)
		andy.setProjectionMatrix(mProiMatrix)

		//andy.draw()

		box.setModelMatrix(modelMatrix)
		box.setViewMatrix(mViewMatrix)
		box.setProjectionMatrix(mProiMatrix)

		//box.draw()

		dog.setModelMatrix(modelMatrix)
		dog.setViewMatrix(mViewMatrix)
		dog.setProjectionMatrix(mProiMatrix)

		dog.draw()

	}
}