package com.example.androidlifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.androidlifecycle.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val TAG: String = "로그"
    //전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityMainBinding? = null
    //매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!

    // 엑티비티가 생성되었을때
    override fun onCreate(savedInstanceState: Bundle?) {

        //메인 엑티비티 -> 엑티비티 메인 바인딩
        //자동으로 완성된 엑티비티 메인 바인딩 클래스 인스턴스를 가져왔다.

        super.onCreate(savedInstanceState)
        //레이아웃 설정
        //기존 setContentView를 제거해주시고...
        //setContentView(R.layout.activity_main)

        //자동 생성된 뷰 바인딩 클래스에서의 inflate라는 메서드를 활용해서
        //엑티비티에서 사용할 바인딩 클래스의 인스턴스 생성
        mBinding = ActivityMainBinding.inflate(layoutInflater)

        //getRoot 메서드로 레이아웃 내부의 최상위 위치 뷰의
        //인스턴스를 활용하여 생성된 뷰를 엑티비티에 표시합니다.
        setContentView(binding.root)

        Log.d(TAG, "MainActivity - onCreate() called")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "MainActivity - onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "MainActivity - onResume() called")
    }

    override fun onPause() {
        super.onPause()

        //뷰 id도 파스칼케이스 + 카멜케이스의 네이밍 규칙 적용으로 인해
        //text_view -> textView로 사용합니다
        binding.textView.visibility = View.VISIBLE
        binding.textView.text = "onPause()"

        Log.d(TAG, "MainActivity - onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "MainActivity - onStop() called")
    }

    override fun onDestroy() {

        //엑티비티가 파괴될때.. binding class 인스턴스 참조를 정리해주어야한다.
        mBinding = null
        super.onDestroy()
        Log.d(TAG, "MainActivity - onDestroy() called")
    }
}