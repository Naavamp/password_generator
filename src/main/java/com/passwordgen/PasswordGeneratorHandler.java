package com.passwordgen;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import com.google.gson.Gson;

import java.util.Map;

public class PasswordGeneratorHandler
        implements RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {
    private static final Map<String, String> CORS_HEADERS = Map.of(
            "Access-Control-Allow-Origin", "*",
            "Access-Control-Allow-Headers", "Content-Type",
            "Access-Control-Allow-Methods", "OPTIONS,POST");

    private final Gson gson = new Gson();
    private final PasswordGenerator passwordGenerator = new PasswordGenerator();
    private final StrengthEvaluator strengthEvaluator = new StrengthEvaluator();

    @Override
    public APIGatewayV2HTTPResponse handleRequest(APIGatewayV2HTTPEvent request, Context context) {
        return toResponseEvent(request);
    }

    private APIGatewayV2HTTPResponse toResponseEvent(APIGatewayV2HTTPEvent request) {
        try {
            PasswordRequest passwordRequest = gson.fromJson(request.getBody(), PasswordRequest.class);
            String password = passwordGenerator.generate(passwordRequest);
            PasswordResponse passwordResponse = strengthEvaluator.evaluate(password);

            return APIGatewayV2HTTPResponse.builder()
                    .withStatusCode(200)
                    .withHeaders(CORS_HEADERS)
                    .withBody(gson.toJson(passwordResponse))
                    .build();
        } catch (Exception exception) {
            exception.printStackTrace();
            return APIGatewayV2HTTPResponse.builder()
                    .withStatusCode(500)
                    .withHeaders(CORS_HEADERS)
                    .withBody(stackTrace(exception))
                    .build();
        }
    }

    private String stackTrace(Exception exception) {
        java.io.StringWriter writer = new java.io.StringWriter();
        exception.printStackTrace(new java.io.PrintWriter(writer));
        return writer.toString();
    }
}
