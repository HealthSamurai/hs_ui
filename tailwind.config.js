module.exports = {
  content: ["./src/**/*.{clj,cljc,cljs}"],
  darkMode: 'selector',
  theme: {
    extend: {
      borderRadius: {
        'xs': '2px',
        'S':  '4px',
        'm':  '6px',
        'l':  '8px'
      },
      padding: {
        'x1': '8px',
        'x3': '24px'
      },
      margin: {
        'x1': '8px',
        'x3': '24px'
      },
      boxShadow: {
        'button-primary':  '0px 1px 2px 0px rgba(0, 0, 0, 0.05)',
        'button-critical': '0px 1px 2px 0px rgba(0, 0, 0, 0.05)',
        'combobox-popover': '0px 10px 15px -3px rgba(0, 0, 0, 0.10), 0px 4px 6px -2px rgba(0, 0, 0, 0.05)'
      },
      backgroundColor: {
        'button-primary-default':    '#2278E1',
        'button-primary-hovered':    '#0960CA',
        'button-primary-disabled':   '#DBDDE3',
        'button-critical-default':   '#DF351F',
        'button-critical-hovered':   '#BE2F1C',
        'button-critical-disabled':  '#DBDDE3',
        'button-secondary-default':  '#FFF',
        'button-secondary-hovered':  '#F9F9F9',
        'listItem-hovered':  '#F9F9F9',
        'button-secondary-disabled': '#DBDDE3',
        'button-xs':                 '#F9FAFB',
        'button-xs-hovered':         'transparent',
        'input-background': '#FFF'
      },
      colors: {
        'link': '#226AE1',
        'icon': '#83868E',
        'button-primary-text':  '#FFF',
        'button-critical-text': '#FFF',
        'button-secondary-text-default': '#90959F',
        'button-secondary-text-hovered': '#1D2331',
        'button-secondary-text-disabled': '#CCCED3',
        'border-default': '#CCCED3',
        'button-tertiary-text-default': '#90959F',
        'button-tertiary-text-hovered': '#1D2331',
        'button-tertiary-text-disabled': '#CCCED3',
        'button-xs-text': '#2278E1',
        'button-xs-text-disabled': '#D3E9FE'
      },
      fontFamily: {
        default: ["Inter", "sans-serif"]
      },
      fontSize: {
        'button-xs': '12px'
      },
      borderColor: {
        'button-xs': '#D3E9FE',
        'button-xs-hovered': '#2278E1',
        'input-default': '#CCCED3'
      },
      lineHeight: {
        'button-xs': '16px'
      }
    }
  },
  plugins: []
}
