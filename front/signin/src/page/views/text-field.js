import { nextTick } from '../utils';
import template from './text-field.template';

const DefaultProps = {
    id: '',
    text: '',
    label: 'label',
    type: 'text',
    placeholder: '',
    require: false,
}

export default class TextField {
    #template = template;
    #container;
    #data;
    #updated = false;
    #validateRules = [];

    constructor(container , data){
        this.#container = container;
        this.#data = {...DefaultProps, ...data};

        nextTick(this.#attachEventHandler);
    }

    #validate = () =>{

    }

    #buildData = ()=> {
        if(this.#update){
            return {
                ...this.#data, 
            }
        }
        else{
            return {
                ...this.#data,                
            }
        }
    }
    #onChange = ()=> {
        
    }
    #attachEventHandler = ()=> {
        document.querySelector(this.#container)?.addEventListener('change',this.#onChange);
    }
    #update = ()=> {
        const container = document.querySelector(`#field-${this.#data.id}`);
        const docFrag = document.createElement('div');

        docFrag.innerHTML = this.#template(this.#buildData());
        container.innerHTML = docFrag.children[0].innerHTML;
    }

    get name(){
        return this.#data.id;
    }
    get value(){
        return this.#data.text;
    }
    
    render = (append) =>{
        const container = document.querySelector(this.#container);

        if (append) {
            const divFragment = document.createElement('div');
            divFragment.innerHTML = this.#template(this.#buildData());

            container.appendChild(divFragment.children[0]);
        }
        else{
            container.innerHTML = this.#template(this.#buildData());
        }
    }
}//SPA single page application