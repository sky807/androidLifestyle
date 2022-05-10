# Android Mobile

### 생명주기 
> 안드로이드 생명주기는 = 메모리와 관련    

사람이 태어나서 죽기까지 과정이 있듯이, 앱도 마찬가지 이다.   
실행되서 꺼지기까지 과정을 생명주기라고 한다.  

생명주기를 알아야 하는 이유는 '메모리' 때문이다.   
예를 들어서, 핸드폰으로 앱을 사용하다가 잠깐 다른 앱이나, 브라우저를 열때가있다.   
만약 사용하던 앱이 이렇게 잠깐 내려놓을 때도 계속 메모리를 차지한다면   
핸드폰의 속도나 성능이 저하될 것이다.    

때문에 각 주기 별로 메모리를 채우고 비우고, 재실행하는 것을 통해서 
효율적으로 메모리를 관리할 수 있다.

사용하는 사람 입장에서는 1. 앱을 켠다. 2. 액티비티가 화면에 보인다. 정도로 생각될지 모르겠으나   
실제로 액티비티가 생성되고 제거되는 데는 여러 가지 단계가 있다.   

생명주기를 이해하고 때에 맞는 적절한 작업을 하면 안정적인 앱을 만들 수 있다.   
그렇게 하지 않으면 발생하는 **대표적인 사례**는 다음과 같다.   

**1. 앱을 사용하다가 다른 앱으로 전환할 때 강제 종료되는 경우**  
: 유튜브 보다가 공유하기를 눌러서 카카오톡을 켰는데 유튜브가 강제 종료된다고 상상해보자   

**2. 앱을 사용하고 있지 않은데 리소스가 낭비되는 경우** 
: 산속에서 GPS를 잡다가 안되서 포기하고 잠금 상태로 두었다고 치자.  
그리고 나중에 다시 폰을 켜보니 앱이 계속 GPS를 잡는다고 배터리를 다 사용했다고 상상해보자  

**3. 앱에서 나갔다가 다시 들어왔는데 사용자의 진행 상태가 저장되지 않은 경우**  
: 게임 다운로드가 너무 오래 걸려서 잠시 카톡 보내고 왔는데 다시 처음부터 받아야 한다고 상상해보자  

**4. 화면 가로/세로 전환을 할 경우, 강제 종료되거나 진행 상태가 저장되지 않은 경우** 
: 누워서 웹툰보다가 반대쪽으로 돌아누웠는데 앱이 갑자기 꺼졌다고 상상해보자  

뭐 대충 이런 상황들이 있다는 것이다.  
생명 주기를 고려하지 않고 앱을 만들면 충분히 생길 수 있는 상황이다.  

**1. onCreate**

```
class MainActivity : AppCompatActivity() {
 
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
```

액티비티를 새로 생성하면 클래스 안에 onCreate가 자동으로 생성되어있는데 
다른 메소드는 없지만 오직 onCreate만 존재한다. 
그 말은 **<u>반드시 구현해야 하는 메서드</u>**란 뜻이다.

onCreate는 액티비티가 생성되면 가장 먼저 실행되는 메서드로 
**화면 레이아웃 정의, 뷰를 생성하거나 데이터 바인딩** 등을 이곳에서 하면된다. 
그리고 생명주기 동안 딱 한번만 실행되는 메서드이기도하다. 
그래서 액티비티 **최초 실행 시에만 해야 할 작업**을 여기서 하면된다.

**2. onStart**

액티비티가 화면에 표시되기 직전에 호출된다. 
**화면에 진입할 때 마다 실행되어야 하는 코드**를 이곳에 작성하면 된다. 
(이 부분은 화면이 사용자에게 표시된다. )

**3. onResume**

다른 액티비티가 액티비티를 덮어버리거나 앱 사용 중 전화가 와서 잠시 앱을 떠나거나
카톡에서 사진첨부할 때 '갤러리'가 아닌 카메라를 클릭해 카메라를 켠다거나 등의 상황으로 
잠시 액티비티가 **일시정지 되었다가 돌아오는 경우** onResume 메서드가 호출된다. 
만약 액티비티가 **재개되었을 때 실행해야 할 코드**가 있다면 이곳에 작성하면 된다.

```
Regardless of which build-up event you choose to perform an initialization operation in,    
make sure to use the corresponding lifecycle event to release the resource. 
If you initialize something after the ON_START event, 
release or terminate it after the ON_STOP event.
If you initialize after the ON_RESUME event, 
release after the ON_PAUSE event.
```

공식 문서를 보면 이렇게 적혀있다. 
onStart에서 초기화 작업을 했다면 -> onStop에서 리소스 해제/종료 작업을 하고
onResume에서 초기화 작업을 했다면 -> onPause에서 리소스 해제/종료 작업을 해라
이걸 염두에 두고 코드를 작성하면 될 것 같다.

**4. onPause**

이벤트가 발생하면 이메서드가 호출된다. 
즉, 사용자가 액티비티를 잠시 떠나기 때문에 
리소스를 계속 소모하거나 GPS가 계속 작동하거나 이럴 필요가 없는 것이다. 
**포그라운드에 있지 않을 때 설명할 필요가 없는 기능**들을 onPause에서 **일시 정지**하면된다.

종료되기 전에 호출되는 메서드가 onStop, onDestroy이렇게 2개나 더 있어서 
여기서 작업해도 되지 않을까 라는 생각이 들지만 
onStop, onDestroy는 메모리가 부족하면 호출되지 않을 수 있다는 문제가 있다. 

```
onPause() execution is very brief, 
and does not necessarily afford enough time to perform save operations. 
For this reason, 
you should not use onPause() to save application or user data,
make network calls, or execute database transactions; 
such work may not complete before the method completes.
```

생명주기 공식 문서페이지에서는 
**onPause는 아주 잠깐 실행되는 메서드**이므로,
데이터를 저장하거나, 네트워크를 호출하는 등 **<u>무거운 작업을 하면 안된다.</u>** 
메서드가 완료되기 전에 작업이 끝나지 않을 수 있다고 경고하고 있다. 
그러면 무거운 작업은 어디서 해야하는가? 바로 다음에 설명할 onStop에서 하면된다. 

**5. onStop**

액티비티가 사용자에게 더 이상 보이지 않으면 이 메서드가 호출된다. 
앞서 말했듯이 무거운 작업을 이곳에서 해주면 된다. 

**6. onRestart**

홈으로 나갔다가 다시 돌아오거나 
다른 액티비티로 갔다가 뒤로가기 버튼을 통해서 돌아오는 경우 이 메서드가 호출된다. 

**7. onDestroy**

앱을 종료하는 경우 호출된다. 
onStop에서 혹시 아직 해제하지 못한 리소스 작업을 하면되는데
앞서 말했듯이 메모리가 부족하면 메서드 호출이 이루어지지 않고 종료 될 수 있다는 것에 유의

| 액티비티 상태                     | 종료될 확률 |
| --------------------------------- | ----------- |
| created<br />started<br />resumed | 낮음        |
| paused                            | 중간        |
| stoped<br />destroyed             | 높음        |


