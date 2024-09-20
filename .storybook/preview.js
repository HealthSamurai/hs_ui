import { themes } from '@storybook/theming';

export const parameters = {
  darkMode: {
    classTarget: 'body',
    stylePreview: true,
    dark:  {...themes.dark, appPreviewBg: 'transparent'},
    light: {...themes.light, appBg: 'white'}
  }
};
