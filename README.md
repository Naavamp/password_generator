# Password Generator

This project is a Java 21 password generator exposed through an AWS Lambda handler and a small browser interface. It creates passwords with lowercase letters by default and can add uppercase letters, numbers, and symbols; it also applies minimum lengths for the `basic`, `standard`, and `paranoid` tiers and returns a strength evaluation with the generated password.

## Run example

Build the deployable JAR from Windows PowerShell:

```powershell
.\gradlew.bat shadowJar
```

Upload `build/libs/password_generator-1.0-SNAPSHOT.jar` to an AWS Lambda function whose handler is `com.passwordgen.PasswordGeneratorHandler`. The handler accepts a `POST` request containing JSON such as:

```json
{
  "length": 16,
  "tier": "standard",
  "includeUppercase": true,
  "includeNumbers": true,
  "includeSymbols": true
}
```

To use the browser interface, replace `YOUR_LAMBDA_FUNCTION_URL` in `index.html` with the Lambda Function URL, then open `index.html` in a browser.

## Test the deployed Lambda from the command line

The repository includes a sample `request.json` file. From the project directory, send it as a `POST` request to the deployed Lambda Function URL. Replace the URL below with your own:

```powershell
curl.exe -i -X POST "https://5ovcjletwzrb7p2vsv6pzn4q3u0fjqyv.lambda-url.us-east-2.on.aws/" `
  -H "Content-Type: application/json" `
  --data-binary "@request.json"
```

The response should contain the generated password, its strength label, entropy, and any failed strength rules.

## Imports

- `java.security.SecureRandom` provides cryptographically stronger random character selection for generated passwords.
- `com.amazonaws.services.lambda.runtime.*` supplies the AWS Lambda handler interface, request context, and API Gateway HTTP event/response types.
- `com.google.gson.Gson` converts the incoming JSON request into Java objects and serializes the response back to JSON.
- `java.util.Map` stores the shared CORS response headers.
- `java.util.ArrayList` and `java.util.List` collect password-strength rules and report any that were not met.

The AWS Lambda and Gson imports are provided by the dependencies in `build.gradle`; the `java.*` imports are part of the Java standard library.
