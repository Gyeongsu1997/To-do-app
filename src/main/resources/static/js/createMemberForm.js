function createMemberForm() {
    // create element (form)
    var newForm = document.createElement('form');
    // set attribute (form)
    newForm.method = 'post';
    newForm.action = '/members/new';

    // create element (input)
    var newInput = document.createElement('input');
    // set attribute (input)
    newInput.setAttribute("type", "text");
    newInput.setAttribute("name", "name");
    newInput.setAttribute("placeholder", "이름을 입력하세요");
    newInput.required = true;

    // create element (button)
    var newButton = document.createElement('button');
    // set attribute (button)
    newButton.setAttribute("type", "submit");
    newButton.setAttribute("class", "btn btn-primary");
    newButton.innerHTML = "등록";

    // append input, button (to form)
    newForm.appendChild(newInput);
    newForm.appendChild(newButton);

    // append form (to body)
    document.body.appendChild(newForm);
}
