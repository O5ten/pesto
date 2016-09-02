package json;

import spark.ResponseTransformer;

class TextTransformer implements ResponseTransformer {

    @Override
    public String render(Object model) {
        return model.toString();
    }
}
