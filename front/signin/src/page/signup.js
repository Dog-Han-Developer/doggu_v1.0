
import template from './signup.template';
import { TextField, PasswordField, AddressField } from './views';

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
    const nameField = new TextField('#required-fields', {
      id: 'name', label: '이름', type: 'text', placeholder: '이름을 입력해주세요', require: true,
    });
    const idField = new TextField('#required-fields', {
      id: 'id', label: '아이디', type: 'text', placeholder: '아이디를 입력해주세요', require: true,
    });
    const passwordField = new TextField('#required-fields', {
      id: 'password', label: '비밀번호', placeholder: '비밀번호를 입력해주세요',
    });
    const emailField = new TextField('#required-fields', {
      id: 'email', label: '이메일', type: 'email', placeholder: '이메일을 입력해주세요', require: true,
    });

    this.#fields.push(nameField);
    this.#fields.push(idField);
    this.#fields.push(emailField);
    this.#fields.push(passwordField);
  }

  #validFieldMonitor = () => {
    
  }

  render = () => {
    this.#container.innerHTML = this.#template(); 
    this.#fields.forEach(field => {
      field.render(true);
    });

    //this.container.addEventListener('submit', this.onSubmit);
  }
}


