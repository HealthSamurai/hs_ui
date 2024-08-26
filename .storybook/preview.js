import '../resources/public/css/tailwind.css';
import { themes } from '@storybook/theming';

export const parameters = {
  darkMode: {
    stylePreview: true,
    dark:  {...themes.dark, appPreviewBg: 'transparent'},
    light: {...themes.light, appPreviewBg: 'transparent'}
  }
};
