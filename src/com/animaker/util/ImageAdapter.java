package com.animaker.util;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageAdapter extends XmlAdapter<String, Image> {

    @Override
    public Image unmarshal(String v) throws Exception {
        BASE64Decoder base64Decoder = new BASE64Decoder();
        ByteArrayInputStream stream = new ByteArrayInputStream(base64Decoder.decodeBuffer(v));
        return new Image(stream);
    }

    @Override
    public String marshal(Image image) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", baos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte pgnBytes[] = baos.toByteArray();
        Base64.Encoder base64_enc = Base64.getEncoder();
        return base64_enc.encodeToString(pgnBytes);
    }
}
