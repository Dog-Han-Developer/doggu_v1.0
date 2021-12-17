
import template from './doginfo.template';
import { TextField, PasswordField, AddressField } from './views';
import axios from 'axios';

export default class Doginfo {
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
    const kindField = new TextField('#required-fields', {
      id: 'kind', label: '견종', type: 'text', placeholder: '견종을 입력해주세요', require: true
    });
    const weightField = new TextField('#required-fields', { 
      id: 'weight', label: '몸무게', type: 'number', placeholder: '몸무게를 입력해주세요', require: true
    });
    const birthField = new TextField('#required-fields', {
      id: 'birth', label: '생일', type: 'date', placeholder: '생일을 입력해주세요', require: true
    });
    const sexField = new TextField('#required-fields', {
      id: 'sex', label: '성별', type: 'text', placeholder: '성별을 입력해주세요', require: true
    });

    this.#fields.push(nameField);
    this.#fields.push(kindField);
    this.#fields.push(weightField);
    this.#fields.push(birthField);
    this.#fields.push(sexField);
  }

  #validFieldMonitor = () => {
    
  }

  render = () => {
    this.#container.innerHTML = this.#template(); 
    this.#fields.forEach(field => {
      field.render(true);
    });

    this.#container.addEventListener('submit', this.#onSubmit);
  }

  #onSubmit = e => {
    e.preventDefault();

    let config = { 
      headers:{
        'Content-Type': 'application/json',
        'Authorization': "Bearer " + sessionStorage.getItem('token')
      }
    }

    let requestBody = {
      dogName: e.target.elements[0].value,
      dogKind: e.target.elements[1].value,
      dogWeight: e.target.elements[2].value,
      dogBirth: e.target.elements[3].value,
      dogSex: e.target.elements[4].value=="여"?"F":"M"
    };

    axios
      .post('https://doggu.kr/api/accounts/dog-info', requestBody,config)
      .then((res) => {
        console.log(res);
        location.href = '/#/pick';
      })
      .catch((e) => {
        console.log(e);
      });
  }
}


