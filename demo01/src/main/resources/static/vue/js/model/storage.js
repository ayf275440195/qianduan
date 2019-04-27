var storage={
    set(key,value){
        localStorage.setItem(key,JSON.stringify(value));
    },
    get(key){
        var value =  localStorage.getItem(key);
        if (value!=null&&value!="undefined"){
            return JSON.parse(value);
        }
        return null;
    },
    remove(key){
        localStorage.removeItem(key)
    }
}

export default storage ;