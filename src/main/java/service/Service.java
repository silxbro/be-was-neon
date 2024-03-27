package service;

import webserver.http.RequestResult;

public interface Service {
    RequestResult execute(String parameterData);
}