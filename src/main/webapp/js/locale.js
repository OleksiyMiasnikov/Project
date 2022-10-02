function setLocale(lang) {
    const urlParams = new URLSearchParams(window.location.search);
    urlParams.set('command', 'change_locale');
   // window.location.search = urlParams;

}