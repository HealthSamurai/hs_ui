
<p align="center">
  <img widht=128 height=128 src="https://cdn.prod.website-files.com/57441aa5da71fdf07a0a2e19/5a2ff50e669ec50001a59b5d_health-samurai.webp" />
  <h3 align="center">Health Samurai Design System</h3>
  <p align="center">
    <br />
    <a href="https://healthsamurai.github.io/hs_ui">View Demo</a>
    <br>
    <br>
    <img src="https://github.com/HealthSamurai/hs_ui/actions/workflows/storybook.yml/badge.svg" />
  </p>
</p>

# Usage
Add the library to deps.edn
```
{:extra-deps {...
              healthsamurai/hs-ui {:local/root "libs/hs_ui"}
              ...}}
```

And then add the following line to your HTML template:
```html
<head>
...
  <link rel="stylesheet" href="css/hs_ui.css">
...
</head>
```

The value of href may differ, it depends on how your HTTP server is
configured. The actual file path in hs_ui is
resources/public/hs_ui.css

# Storybook
```sh
make story 
```

# Development
```sh
make init
make dev
```
