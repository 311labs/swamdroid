# SwamDroid

SwamDroid is an Android library that simplifies the process of building Android applications as web applications using frameworks like SWAM. With SwamDroid, you can effortlessly establish asynchronous communication between web applications and the native device. Additionally, it provides a convenient way to create new native services that can be seamlessly utilized by your web applications.

## Features

- **Web Application Integration:** SwamDroid enables you to build Android applications with the same paradigms and frameworks used in web application development, such as SWAM. This allows for easier code reuse and a consistent development experience.

- **Async Message Communication:** SwamDroid facilitates asynchronous message passing between web applications and the native device. You can effortlessly send and receive messages, enabling efficient and responsive communication channels.

- **Native Service Creation:** With SwamDroid, you can effortlessly create new native services that can be utilized by your web applications. This feature allows for the integration of powerful native functionality into your web-based Android applications.

## Getting Started

Follow the steps below to integrate SwamDroid into your Android project:

1. **Add the SwamDroid Library:** Add the SwamDroid library to your project's dependencies. You can do this by including the following line in your app-level `build.gradle` file:

```groovy
implementation 'io.swamtech.swamview:swamview:1.0.0'
```

2. **Initialize SwamDroid:** In your application's main activity or application class, initialize SwamDroid by calling the `initialize()` method. This sets up the necessary configurations for SwamDroid to function correctly.

```java
import com.example.swamdroid.SwamDroid;

public class MainActivity extends SwamActivity {

    @Override
    protected void initSettings() {
        this.url_active = this.broker.settings.optString("app_url", "file:///android_asset/apps/mobile/index.html");
        this.url_offline = "file:///android_asset/offline.html";
        // enable strict caching to save on network traffic
        this.strictCaching = false;
    
    }
    @Override
    protected void initHandlers() {
    
        this.broker.addHandler("browser", new BrowserHandler(this.broker));
        this.broker.addHandler("system", new SystemHandler(this.broker));
    
    }

}
```

3. **Utilize SwamDroid APIs:** You can now start utilizing the SwamDroid APIs in your application. Refer to the SwamDroid documentation for detailed instructions on how to send and receive async messages between web applications and the native device, as well as creating new native services.

## Documentation

For detailed documentation and usage instructions, please refer to the [SwamDroid Documentation](https://github.com/311labs/swamdroid/wiki).

## Contributing

We welcome contributions to SwamDroid! To contribute, please follow these guidelines:

- Fork the repository and clone it to your local machine.
- Create a new branch for your feature or bug fix.
- Make your modifications and commit them with descriptive commit messages.
- Push your branch to your forked repository.
- Open a pull request in the main SwamDroid repository.

Please refer to our [Contribution Guidelines](https://github.com/311labs/swamdroid/CONTRIBUTING.md) for more details.

## License

SwamDroid is released under the [MIT License](https://github.com/311labs/swamdroid/wiki/LICENSE).
