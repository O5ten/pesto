package response

class Paste {
    String id
    String title
    String code
    String language
    String author
    String published 
    String modified

    Map asMap(){
        this.class.declaredFields.findAll { !it.synthetic }.collectEntries {
            [(it.name):this."$it.name" ]
        }
    }
}
