
import template from './signup.template';
import { TextField, PasswordField, RadioSelectField } from './views';
import axios  from 'axios';

export default class Signup {
  #template = template;
  #data;
  #container;
  #fields = [];
  #active = false;

  constructor(container, data={}) {
    this.#container = document.querySelector(container);
    this.#data = data;
    this.#fields=[];

    this.#initialize();

    setInterval(this.#validFieldMonitor, 1000/30);
  }

  #initialize = () => {
    const userNameField = new TextField('#required-fields', {
      id: 'userName', label: '이름', type: 'text', name: 'userName', placeholder: '이름을 입력해주세요', require: true,
    });
    const emailField = new TextField('#required-fields', {
      id: 'email', label: '이메일', type: 'email', name: 'email', placeholder: '이메일을 입력해주세요', require: true,
    });
    const passwordField = new PasswordField('#required-fields', {
      id: 'password', label: '비밀번호', name: 'password', placeholder: '비밀번호를 입력해주세요',
    });
    const radioField = new RadioSelectField('#required-fields', {
      id: 'radio', label:'견주인가요?', name: 'isOwner',
    });

    this.#fields.push(userNameField);
    this.#fields.push(emailField);
    this.#fields.push(passwordField);
    this.#fields.push(radioField);
  }

  #validFieldMonitor = () => {
  }

  #onSubmit = e => {
    e.preventDefault();
    console.log(e.target.isOwner.value);
    let payload = { email : e.target.email.value,  userName: e.target.userName.value, password: e.target.password.value};
    axios.post('https://doggu.kr/api/accounts', payload)
    .then(res => {
      console.log(res);
      location.href = '/#/pick';
    })
    .catch(e => {
      // console.log(e);
      // let errorMessages;
      // e.errors.forEach(errorMessage => {
      //   errorMessages += errorMessage + "\n";
      // })
      // alert("아래의 사항에 대해서 수정이 필요합니다" + "\n" + errorMessages);
    })

    let requestBody = { "registerNumber" : e.target.registerNumber.value, "ownerName" : e.target.ownerName.value };

    axios.post('https://doggu.kr/api/accounts/dog-info', requestBody, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer' + sessionStorage.getItem('token')
      }
    })
    .then((res) => {
      console.log(res);
      location.href = '/#/pick';
    })
    .catch((e) => {
      console.log(e);
    });
    return false;
  }

  render = () => {
    this.#container.innerHTML = this.#template(); 
    this.#fields.forEach(field => {
      field.render(true);
    });

    this.#container.addEventListener('submit', this.#onSubmit);
  }
}


