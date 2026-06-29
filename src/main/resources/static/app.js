if ('serviceWorker' in navigator) {
  navigator.serviceWorker
    .register('/static/sw.js', { scope: '/' })
    .then((registration) => {
      console.log('Service Worker registered with scope:', registration.scope);
    })
    .catch((error) => {
      console.error('Service Worker registration failed:', error);
    });

  let deferredPrompt;
  window.addEventListener('beforeinstallprompt', e => {
    e.preventDefault();
    deferredPrompt = e;

    // Build and show dialog
    const dialog = document.createElement('dwc-dialog');
    dialog.maxWidth = '400px';

    // Header
    const header = document.createElement('h3');
    header.slot = 'header';
    header.textContent = 'Install App';
    dialog.appendChild(header);

    // Content
    const content = document.createElement('div');
    content.slot = 'content';
    content.innerHTML = '<p>This application can be installed on your device. Install it now?</p>';
    dialog.appendChild(content);

    // Footer with buttons
    const footer = document.createElement('div');
    footer.slot = 'footer';

    const cancelBtn = document.createElement('dwc-button');
    cancelBtn.textContent = 'Cancel';
    cancelBtn.theme = 'default';
    footer.appendChild(cancelBtn);

    const installBtn = document.createElement('dwc-button');
    installBtn.textContent = 'Install';
    installBtn.theme = 'primary';
    footer.appendChild(installBtn);

    dialog.appendChild(footer);
    document.body.appendChild(dialog);
    dialog.opened = true;

    // Button handlers
    installBtn.addEventListener('click', async () => {
      dialog.opened = false;
      deferredPrompt.prompt();
      const choice = await deferredPrompt.userChoice;
      console.log(choice.outcome === 'accepted'
        ? 'User accepted install'
        : 'User dismissed install');
      deferredPrompt = null;
      dialog.remove();
    });

    cancelBtn.addEventListener('click', () => {
      dialog.opened = false;
      deferredPrompt = null;
      console.log('User cancelled install dialog');
      dialog.remove();
    });
  });
}
