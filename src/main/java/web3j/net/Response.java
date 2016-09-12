package web3j.net;

/**
 * Created by gunicolas on 18/08/16.
 */
public class Response<T> {

    public int id;
    public T result;
    public Error error;
    public Request request;

    public class Error{
        public int code;
        public String message;
    }

    public Response(int id, T result,Error error,Request request) {
        this.id = id;
        this.result = result;
        this.error = error;
        this.request = request;
    }

    public boolean isError(){
        return error != null;
    }

}
