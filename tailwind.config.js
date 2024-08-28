module.exports = {
  content: ["./src/**/*.{clj,cljc,cljs}"],
  darkMode: 'selector',
  theme: {
    extend: {
      borderRadius: {
        'xs': '2px',
        's':  '4px',
        'm':  '6px',
        'l':  '8px'
      },
      backgroundColor: {
        'button-primary-default':   '#2278E1',
        'button-primary-hovered':   '#0960CA',
        'button-primary-disabled':  '#DBDDE3',
        'button-critical-default':  '#DF351F',
        'button-critical-hovered':  '#BE2F1C',
        'button-critical-disabled': '#DBDDE3',
        'button-secondary-default':  '#FFF',
        'button-secondary-hovered':  '#F9F9F9',
        'button-secondary-disabled': '#DBDDE3'
      },
      colors: {
        'link': '#226AE1',
        'icon': '#83868E',
        'button-primary-text':  '#FFF',
        'button-critical-text': '#FFF',
        'button-secondary-text-default': '#90959F',
        'button-secondary-text-hovered': '#1D2331',
        'button-secondary-text-disabled': '#CCCED3',
        'border-default': '#EBECEE',
        'button-tertiary-text-default': '#90959F',
        'button-tertiary-text-hovered': '#1D2331',
        'button-tertiary-text-disabled': '#CCCED3',
      },
      padding: {
        'x1': '8px',
        'x3': '24px'
      },
      margin: {
        'x1': '8px',
        'x3': '24px'
      },
      fontFamily: {
        default: ["Inter", "sans-serif"]
      },
      boxShadow: {
        'button-primary':  '0px 1px 2px 0px rgba(0, 0, 0, 0.05)',
        'button-critical': '0px 1px 2px 0px rgba(0, 0, 0, 0.05)'
      }
    }
  },
  plugins: []
}
