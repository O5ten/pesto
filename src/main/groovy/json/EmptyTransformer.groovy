package json;

import spark.ResponseTransformer;

class EmptyTransformer implements ResponseTransformer {

    @Override
    public String render(Object model) {
        return "";
    }
}
