
export default {
  bootstrap: () => import('./main.server.mjs').then(m => m.default),
  inlineCriticalCss: true,
  baseHref: '/',
  locale: undefined,
  routes: [
  {
    "renderMode": 2,
    "route": "/"
  },
  {
    "renderMode": 2,
    "redirectTo": "/",
    "route": "/**"
  }
],
  entryPointToBrowserMapping: undefined,
  assets: {
    'index.csr.html': {size: 654, hash: '09592e94fe69c85ea787e7b97224f4522531325aef21a1ea41d6784140fb135d', text: () => import('./assets-chunks/index_csr_html.mjs').then(m => m.default)},
    'index.server.html': {size: 1007, hash: 'af0c13dcd9dc4fa88a48a16e153cf0ef7c4531d9906a0af9274d821c2d53362d', text: () => import('./assets-chunks/index_server_html.mjs').then(m => m.default)},
    'index.html': {size: 6201, hash: 'ea6b6b410bbab9357bd8092c9e5c52796a32e70342b24ae72e0ffce6f7de2042', text: () => import('./assets-chunks/index_html.mjs').then(m => m.default)},
    'styles-H4W6TKTK.css': {size: 35, hash: 'kfakPabGhjw', text: () => import('./assets-chunks/styles-H4W6TKTK_css.mjs').then(m => m.default)}
  },
};
