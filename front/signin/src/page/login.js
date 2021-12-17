import template from './login.template';
import TextField from '../views/text-field';
import axios from 'axios';

export default class Login {
  #template = template;
  #data;
  #container;
  #loginFail = false;
  #fields = [];

  constructor(container, data) {
    this.#container = document.querySelector(container);
    this.#data = data;

    this.#initialize();
  }

  #initialize = () => {
    const idField = new TextField('#login-fields', {
      id: 'userid',
      label: '아이디',
      type: 'text',
      placeholder: '아이디를 입력해주세요',
      require: true,
    });
    const passwordField = new TextField('#login-fields', {
      id: 'password',
      label: '비밀번호',
      type: 'password',
      placeholder: '**********',
      require: true,
    });

    this.#fields.push(idField);
    this.#fields.push(passwordField);
  };

  #onSubmit = (e) => {
    console.log(e);
    e.preventDefault();

    console.log(e.target.elements[0].value);
    console.log(e.target.elements[1].value);

    let payload = {
      email: String(e.target.elements[0].value),
      password: String(e.target.elements[1].value),
    };

    axios
      .post('https://doggu.kr/api/accounts/login', payload)
      .then((res) => {
        //백에서 던져준 값이 res임

        console.log(res);
        console.log(res.data.accessToken);
        sessionStorage.setItem('token', res.data.accessToken);
        location.href = '/#/pick';
      })
      .catch((e) => {
        console.log(e);
      });
  };

  render = () => {
    this.#container.innerHTML = this.#template({
      ...this.#data,
      loginFail: this.#loginFail,
    });
    this.#fields.forEach((field) => {
      field.render(true);
    });

    this.#container.addEventListener('submit', this.#onSubmit);
  };
}
