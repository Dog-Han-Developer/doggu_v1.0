const template = `
  <div id="field-{{id}}" class="mt-4">
    <div class="flex items-start mb-1">
      <span class="flex items-center {{#if person}}hidden{{/if}}">
        <img src="images/paw.png" style="width:1.25rem; margin-right:0.4rem">
      </span>
      <label class="block text-sm text-white" for="name">{{label}}</label>
    </div>
    <div class="flex">
      <input id="{{id}}" name="{{id}}" type="{{type}}" value="{{text}}" {{#if require}}required{{/if}} 
        placeholder="{{placeholder}}" aria-label="Name" class="w-full px-5 py-1 text-gray-700 {{#if valid}}bg-gray-200{{else}}bg-white{{/if}} rounded">
      {{#if btn}}<button id="{{id}}" class="w-32 px-4 py-1 text-white font-light tracking-wider rounded" style = "background-color: #89806F" type="submit">{{btn-message}}</button>{{/if}}
    </div>
    {{#unless valid}}
    <div class="flex items-start mb-1">
      <label class="block text-sm text-red-300" for="cus_email">{{validateMessage}}</label>
    </div>
    {{/unless}}
  </div>
`;

export default window.Handlebars.compile(template);
