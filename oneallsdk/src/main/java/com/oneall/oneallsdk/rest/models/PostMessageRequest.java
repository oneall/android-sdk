package com.oneall.oneallsdk.rest.models;

import java.util.ArrayList;
import java.util.Collection;

public class PostMessageRequest {
    public static class Request {
        public static class Message {
            public static class Parts {

                private static class Link {
                    public String url;
                    public String name;
                    public String caption;
                    public String description;
                }

                public static class Text {
                    public String body;
                }

                public static class Flags {
                    public Integer enableTracking;
                }

                public Text text;
                public Link picture;
                public Link video;
                public Link link;
                public Flags flags;
            }

            public Parts parts;
            public Collection<String> providers;
        }
        public Message message;
    }

    public Request request;

    public PostMessageRequest(
            Collection<String> providers,
            String text,
            String pictureUrl,
            String videoUrl,
            String linkUrl,
            String linkName,
            String linkCaption,
            String linkDescription,
            Boolean enableTracking) {
        this.request = new Request();
        this.request.message = new Request.Message();
        this.request.message.providers = new ArrayList<>(providers);
        this.request.message.parts = new Request.Message.Parts();
        this.request.message.parts.text = new Request.Message.Parts.Text();
        this.request.message.parts.text.body = text;
        if (pictureUrl != null && pictureUrl.length() > 0) {
            this.request.message.parts.picture = new Request.Message.Parts.Link();
            this.request.message.parts.picture.url = pictureUrl;
        }
        if (videoUrl != null && videoUrl.length() > 0) {
            this.request.message.parts.video = new Request.Message.Parts.Link();
            this.request.message.parts.video.url = videoUrl;
        }
        if (linkUrl != null && linkUrl.length() > 0) {
            this.request.message.parts.link = new Request.Message.Parts.Link();
            this.request.message.parts.link.url = linkUrl;
            if (linkName != null && linkName.length() > 0) {
                this.request.message.parts.link.name = linkName;
            }
            if (linkCaption != null && linkCaption.length() > 0) {
                this.request.message.parts.link.caption = linkCaption;
            }
            if (linkDescription != null && linkDescription.length() > 0) {
                this.request.message.parts.link.description = linkDescription;
            }
        }
        this.request.message.parts.flags = new Request.Message.Parts.Flags();
        this.request.message.parts.flags.enableTracking = enableTracking ? 1 : 0;
    }
}