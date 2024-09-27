import { themes } from '@storybook/theming';

// Uncomment the line below to enable hot-reloading
// import '../resources/public/css/tailwind.css'

export const parameters = {
  darkMode: {
    classTarget: 'body',
    stylePreview: true,
    dark:  {...themes.dark, appPreviewBg: '#0c121b',  appBg: '#0c121b'},
    light: {...themes.light, appBg: 'white'}
  }
};
