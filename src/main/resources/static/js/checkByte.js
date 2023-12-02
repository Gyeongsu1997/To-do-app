function checkByte(obj){
    const maxByte = 255; //최대 255바이트
    const text_val = obj.value; //입력한 문자
    const text_len = text_val.length; //입력한 문자수
    var max_len = 0;
    var sub_str = "";

    let totalByte=0;
    for(let i=0; i<text_len; i++){
        const each_char = text_val.charAt(i);
        const uni_char = escape(each_char); //유니코드 형식으로 변환
        if(uni_char.length>4){
            // 한글 : 2Byte
            totalByte += 2;
        }else{
            // 영문,숫자,특수문자 : 1Byte
            totalByte += 1;
        }
        if (totalByte <= maxByte)
            max_len = i+1;
    }

    if(totalByte>maxByte){
        alert('최대 255Byte까지만 입력가능합니다.');
        sub_str = text_val.substr(0,max_len);
        obj.value = sub_str;
        checkByte(obj);
    }else if (totalByte == maxByte) {
        document.getElementById("nowByte").innerText = totalByte;
        document.getElementById("nowByte").style.color = "red";
    }
    else{
        document.getElementById("nowByte").innerText = totalByte;
        document.getElementById("nowByte").style.color = "green";
    }
}