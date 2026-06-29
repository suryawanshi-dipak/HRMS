self.addEventListener('install', () => self.skipWaiting());
self.addEventListener('activate', () => self.clients.claim());
self.addEventListener('fetch', event => {
  // network-only: fetch every request without caching
  event.respondWith(fetch(event.request));
});
