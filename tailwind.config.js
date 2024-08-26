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
        'button-critical-disabled': '#DBDDE3'
      },
      colors: {
        'button-primary-text':  '#FFF',
        'button-critical-text': '#FFF'
      },
      padding: {
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
