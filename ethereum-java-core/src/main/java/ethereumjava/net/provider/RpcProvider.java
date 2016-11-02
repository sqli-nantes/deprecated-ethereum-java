package ethereumjava.net.provider;

import ethereumjava.exception.EthereumJavaException;
import ethereumjava.net.Request;
import ethereumjava.net.Response;
import rx.Observable;
import rx.Subscriber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * Created by gunicolas on 18/08/16.
 */
public class RpcProvider extends AbstractProvider {

	HttpURLConnection connection;
	URL url;
	String address;

	public RpcProvider(String address) {
		super();
		this.address = address;
	}

	@Override
	public void init() {
		try {
			url = new URL(address);
		} catch (MalformedURLException e) {
			throw new EthereumJavaException(e);
		}
	}

	@Override
	public void stop() throws EthereumJavaException {
	}

	@Override
	protected Observable send(byte[] stringRequest, final Request request) {

		try {
			URLConnection urlConn = url.openConnection();
			this.connection = (HttpURLConnection) urlConn;
			this.connection.setAllowUserInteraction(false);
			this.connection.setInstanceFollowRedirects(true);
			this.connection.setRequestMethod("POST");

			this.connection.setDoInput(true);
			this.connection.setDoOutput(true);
			this.connection.setChunkedStreamingMode(0);
			this.connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			this.connection.setRequestProperty("Accept", "application/json");
			this.connection.connect();

			outputStream = this.connection.getOutputStream();
			outputStream.write(stringRequest);
			outputStream.flush();
			inputStream = this.connection.getInputStream();

			in = new BufferedReader(new InputStreamReader(inputStream, CHARSET));

			final String line;
			if( in.ready() && (line = in.readLine()) != null ){
				requestQueue.put(request.getId(),request);

				Observable ret = Observable.create(new Observable.OnSubscribe<Object>() {
					@Override
					public void call(Subscriber<? super Object> subscriber) {
						Response response = gson.fromJson(line, Response.class);
						if (response.request != null) {
							if (response.isError()) {
								subscriber.onError(new EthereumJavaException(response.error.message));
							} else {
								subscriber.onNext(response.result);
								subscriber.onCompleted();
							}
						}
						requestQueue.remove(response.id);
					}
				});

				return ret;

			}

		} catch (IOException e) {
			throw new EthereumJavaException(e);
		} finally {
			this.connection.disconnect();
			try {
				if (outputStream != null) {
					outputStream.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				throw new EthereumJavaException(e);
			}
		}
		return null;
	}

}
