(ns hs-ui.core
  (:require
   [hs-ui.text]
   [hs-ui.graphic]

   [hs-ui.components.button]
   [hs-ui.components.input]
   [hs-ui.components.checkbox]
   [hs-ui.components.content-expand]
   [hs-ui.components.breadcrumbs]
   [hs-ui.components.list-item]
   [hs-ui.components.list-items]
   [hs-ui.components.alert]
   [hs-ui.components.radio]
   [hs-ui.components.radio-button]
   [hs-ui.components.kvlist]

   [hs-ui.organisms.checkbox]
   [hs-ui.organisms.input]
   [hs-ui.organisms.search-input]
   [hs-ui.organisms.radio]
   [hs-ui.organisms.radio-blocks]
   [hs-ui.organisms.dropdown]
   [hs-ui.organisms.header]

   [hs-ui.layout]
   ))

(def button   hs-ui.components.button/component)
(def input    hs-ui.components.input/component)
(def radio    hs-ui.components.radio/component)
(def radio-button hs-ui.components.radio-button/component)
(def checkbox hs-ui.components.checkbox/component)
(def content-expand hs-ui.components.content-expand/component)
(def breadcrumbs hs-ui.components.breadcrumbs/component)

(def list-item  hs-ui.components.list-item/component)
(def list-items hs-ui.components.list-items/component)
(def kvlist     hs-ui.components.kvlist/wrapper)

(def alert hs-ui.components.alert/component)

(def org-checkbox     hs-ui.organisms.checkbox/component)
(def org-input        hs-ui.organisms.input/component)
(def org-search-input hs-ui.organisms.search-input/component)
(def org-radio        hs-ui.organisms.radio/component)
(def org-radio-blocks hs-ui.organisms.radio-blocks/component)
(def org-dropdown     hs-ui.organisms.dropdown/component)
(def org-header       hs-ui.organisms.header/component)

(def text-page-header          hs-ui.text/page-header)
(def text-section-header       hs-ui.text/section-header)
(def text-button-label-regular hs-ui.text/button-label-regular)
(def text-label                hs-ui.text/label)
(def text-link                 hs-ui.text/link)
(def text-value                hs-ui.text/value)
(def text-body                 hs-ui.text/body)
(def text-code                 hs-ui.text/code)
(def text-counter              hs-ui.text/counter)
(def text-button-label-xs      hs-ui.text/button-label-xs)
(def text-assistive            hs-ui.text/assistive)
(def text-home                 hs-ui.text/home)

(def separator hs-ui.graphic/separator)

(def layout-confirmation        hs-ui.layout/confirmation)
(def layout-nest                hs-ui.layout/nest)
(def layout-navbar              hs-ui.layout/navbar)
(def layout-expandeable-control hs-ui.layout/expandeable-control)
(def layout-control             hs-ui.layout/control)

(def button-primary   hs-ui.components.button/primary)
(def button-critical  hs-ui.components.button/critical)
(def button-secondary hs-ui.components.button/secondary)
(def button-tertiary  hs-ui.components.button/tertiary)
(def button-xs        hs-ui.components.button/xs)
(def button-xs-red    hs-ui.components.button/xs-red)
(def button-slim      hs-ui.components.button/slim)
