
import template from './dogauthorization.template';
import { TextField, PasswordField, AddressField } from './views';
import axios from 'axios';

export default class DogAuthorization {
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
    const registerNumberField = new TextField('#required-fields', {
      id: 'registernumber', label: '동물등록번호', type: 'text', placeholder: '동물등록번호를 입력해주세요', require: true, name: 'registerNumber'
    });
    const ownerField = new TextField('#required-fields', {
      id: 'ownername', label: '견주 이름', type: 'text', placeholder: '견주 이름을 입력해주세요', require: true, name: 'ownerName'
    });

    this.#fields.push(registerNumberField);
    this.#fields.push(ownerField);
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
      "registerNumber" : e.target.registernumber.value, 
      "ownerName" : e.target.ownername.value 
    };

    axios.post('https://doggu.kr/api/accounts/dog-authentication', requestBody, config)
    .then((res) => {
      console.log(res);
      location.href = '/#/doginfo';
    })
    .catch((e) => {
      console.log(e);
    }
    );
  }
}


