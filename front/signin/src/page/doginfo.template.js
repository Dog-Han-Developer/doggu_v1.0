const template = `
<div class="min-h-screen py-6 flex flex-col justify-center sm:py-12" style="background-color:#CBC1AF">
  <div class="relative py-3 md:max-w-xl w-screen md:mx-auto">
    <div class="leading-loose">
      <form id="sign-up-form" method = "POST" class="max-w-screen-2xl m-4 p-10 rounded-lg shadow-xl" style="background-color:#BFB198" >
        <p class="text-white text-xl font-black mb-5 text-center">반려견 등록</p>
        <div id="required-fields">
        </div>
        <div class="mt-4">
          <button id="btn-join" class="w-full mt-5 px-4 py-1 text-white font-light tracking-wider rounded-lg" style = "background-color: #89806F" type="submit">반려견 등록</button>
        </div>    
      </form>
    </div>
  </div>
</div>
`;

export default Handlebars.compile(template);
