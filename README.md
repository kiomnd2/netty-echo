# Netty-Echo-Server
> netty 의 기본인 Netty Server를 만들어 보고 테스트 합니다.

### note

####1. ChannelHandler
> 인바운드 메시지에 반응하는 메서드가 정의되어 있는 인터페이스
* channelRead() : 메시지가 들어올 때마다 호출
* channelReadComplete() : channelRead()의 마지막 호출에서 현재 일괄 처리의 마지막 메시지를 처리했을을 핸들러에 통보
* exceptionCaught(): 읽기 작업 중 예외가 발생하면 호출

####2. Channel, EventLoop, ChannelFuture
* Channel : 소캣
* EventLoop: 제어흐름, 멀티스레딩, 동시설 제어
* ChannelFuture: 비동기 알림



### Refenence
* 네티  인 액션 - netty를 이용한 자바 기반의 고성능 서버& 클라이은트 개발 / 위키북스 / 노먼 마우러