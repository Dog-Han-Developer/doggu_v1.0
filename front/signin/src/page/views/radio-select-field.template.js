const template = `
  <div id="radio-{{id}}" class="mt-4">
    <div class="flex items-start mb-1">
      <span class="flex items-center">
        <svg class="flex-shrink-0 h-5 w-5 {{#if valid}}{{#if updated}}text-green-500{{else}}text-gray-200{{/if}}{{else}}text-gray-200{{/if}}" viewBox="0 0 20 20" fill="currentColor">
          <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd" />
        </svg>
      </span>
      <label class="block text-sm" for="name">{{label}}</label>
    </div>
    <div>
      <div>
        <input type="radio" id="radio-{{id}}-true"
        name="{{name}}" value="true">
        <label for="radio-{{id}}-true">Yes</label>

        <input type="radio" id="radio-{{id}}-false"
        name="{{name}}" value="false">
        <label for="radio-{{id}}-false">No</label>
      </div>
    </div>
    {{#unless valid}}
    <div class="flex items-start mb-1">
      <label class="block text-sm text-red-300" for="cus_email">{{validateMessage}}</label>
    </div>
    {{/unless}}
  </div>
`;

export default window.Handlebars.compile(template);
