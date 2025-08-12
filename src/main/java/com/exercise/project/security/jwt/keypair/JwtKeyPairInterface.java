package com.exercise.project.security.jwt.keypair;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface JwtKeyPairInterface {

    public PrivateKey getPrivateKey();

    public PublicKey getPublicKey();

}
