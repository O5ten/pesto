package response;

class SystemInfo {
    Map properties;
    Map asMap(){
        this.class.declaredFields.findAll { !it.synthetic }.collectEntries {
            [ (it.name):this."$it.name" ]
        }
    }
}
