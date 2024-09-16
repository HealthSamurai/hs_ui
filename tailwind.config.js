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
      spacing: {
        "half": "4px"
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
        'combobox-popover': '0px 10px 15px -3px rgba(0, 0, 0, 0.10), 0px 4px 6px -2px rgba(0, 0, 0, 0.05)',
        'input-default': '0px 0px 0px 1px #D1D5DB inset, 0px 1px 2px 0px rgba(0, 0, 0, 0.05)'
      },
      textColor: {
        'elements-readable': '#1D2331',
        'color-elements-disabled': '#CCCED3'
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
        'listItem-selected':  '#EBECEE',
        'button-secondary-disabled': '#DBDDE3',
        'button-xs':                 '#F9FAFB',
        'button-xs-hovered':         'transparent',
        'input-background': '#FFF'
      },
      colors: {
        'color-surface-1': '#F9F9F9',
        'color-elements-assistive': '#727885',
        'color-elements-readable': '#1D2331',
        'color-cta': '#2278E1',
        'color-border-default': '#CCCED3',
        'color-separator': '#EBECEE',

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
        'button-xs-text-disabled': '#D3E9FE',
      },
      fontFamily: {
        default: ["Inter", "sans-serif"],
        heading: ["Metropolis", "sans-serif"]
      },
      fontSize: {
        'button-xs': '12px',
        'assistive': ['12px', '18px'],
        'section-header': ['16px', '28px'],
        'page-header': ['22px', '24px']
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
