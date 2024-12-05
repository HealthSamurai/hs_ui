const config = {
  stories: [
    "../resources/public/js/storybook/**/*_stories.js",
    "../src/storybook/**/*.mdx"
  ],
  addons: [
    "@storybook/addon-docs",
    "@storybook/addon-controls",
    "@storybook/addon-a11y",
    "storybook-dark-mode"
  ],
  staticDirs: ['../resources/public'],
  framework: {
    name: "@storybook/react-webpack5",
    options: {
      builder: {
        useSWC: true,
      }
    }
  },
  features: {
    storyStoreV7: false
  }
};

export default config;
