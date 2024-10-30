import de.thi.inf.sesa.simplegrpc.HelloRequest;
import de.thi.inf.sesa.simplegrpc.HelloResponse;
import de.thi.inf.sesa.simplegrpc.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;

public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {
    @Override
    public void hello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        String firstName = request.getFirstName();
        String lastName = request.getLastName();

        HelloResponse response = HelloResponse.newBuilder()
                .setGreeting("Hello " + firstName + " " + lastName)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}