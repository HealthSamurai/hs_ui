(ns hs-ui.core
  (:require
   [hs-ui.text]
   [hs-ui.graphic]

   [hs-ui.elements.breadcrumb]
   [hs-ui.elements.table]
   [hs-ui.elements.diff-table]

   [hs-ui.components.button]
   [hs-ui.components.input]
   [hs-ui.components.checkbox]
   [hs-ui.components.checkbox-square]
   [hs-ui.components.content-expand]
   [hs-ui.components.breadcrumbs]
   [hs-ui.components.list-item]
   [hs-ui.components.list-items]
   [hs-ui.components.alert]
   [hs-ui.components.radio]
   [hs-ui.components.radio-button]
   [hs-ui.components.kvlist]
   [hs-ui.components.dialog]
   [hs-ui.components.chip]
   [hs-ui.components.tab]
   [hs-ui.components.tooltip]
   [hs-ui.components.monaco]
   [hs-ui.components.pagination]
   [hs-ui.components.sidebar]

   [hs-ui.organisms.checkbox]
   [hs-ui.organisms.input]
   [hs-ui.organisms.search-input]
   [hs-ui.organisms.textarea]
   [hs-ui.organisms.radio]
   [hs-ui.organisms.radio-blocks]
   [hs-ui.organisms.dropdown]
   [hs-ui.organisms.header]
   [hs-ui.organisms.tabs]
   [hs-ui.organisms.resource-editor]

   [hs-ui.layout]

   [hs-ui.svg.upload]
   [hs-ui.svg.external-link]
   [hs-ui.svg.file-check]
   [hs-ui.svg.plus]
   [hs-ui.svg.chevron-down]
   [hs-ui.svg.pin]
   [hs-ui.svg.ban]
   [hs-ui.svg.trash]
   [hs-ui.svg.cross]))

;; Elements
(def table hs-ui.elements.table/view)
(def diff-table hs-ui.elements.diff-table/view)

(def button   hs-ui.components.button/component)
(def input    hs-ui.components.input/component)
(def textarea hs-ui.components.textarea/component)
(def radio    hs-ui.components.radio/component)
(def radio-button hs-ui.components.radio-button/component)
(def checkbox hs-ui.components.checkbox/component)
(def checkbox-square hs-ui.components.checkbox-square/component)
(def content-expand hs-ui.components.content-expand/component)
(def breadcrumbs hs-ui.components.breadcrumbs/component)
(def breadcrumbs-items hs-ui.elements.breadcrumb/view)
(def chip hs-ui.components.chip/component)
(def tab         hs-ui.components.tab/component)

(def list-item  hs-ui.components.list-item/component)
(def list-items hs-ui.components.list-items/component)
(def kvlist     hs-ui.components.kvlist/component)
(def pagination hs-ui.components.pagination/component)

(def alert hs-ui.components.alert/component)

(def org-checkbox        hs-ui.organisms.checkbox/component)
(def org-input           hs-ui.organisms.input/component)
(def org-search-input    hs-ui.organisms.search-input/component)
(def org-textarea        hs-ui.organisms.textarea/component)
(def org-radio           hs-ui.organisms.radio/component)
(def org-radio-blocks    hs-ui.organisms.radio-blocks/component)
(def org-dropdown        hs-ui.organisms.dropdown/component)
(def org-header          hs-ui.organisms.header/component)
(def org-tabs            hs-ui.organisms.tabs/component)
(def org-secondary-tabs  hs-ui.organisms.tabs/secondary-tabs)
(def org-resource-editor hs-ui.organisms.resource-editor/component)

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
(def layout-vertical-split-view hs-ui.layout/vertical-split-view)
(def layout-horizontal-split-view hs-ui.layout/horizontal-split-view)
(def layout-dropdown hs-ui.layout/dropdown)

(def button-primary   hs-ui.components.button/primary)
(def button-critical  hs-ui.components.button/critical)
(def button-secondary hs-ui.components.button/secondary)
(def button-tertiary  hs-ui.components.button/tertiary)
(def button-xs        hs-ui.components.button/xs)
(def button-xs-red    hs-ui.components.button/xs-red)
(def button-slim      hs-ui.components.button/slim)


(def dialog          hs-ui.components.dialog/component)
(def dialog-template hs-ui.components.dialog/template)

(def tooltip hs-ui.components.tooltip/component)

(def monaco hs-ui.components.monaco/component)

(def sidebar hs-ui.components.sidebar/component)

(def icon-upload hs-ui.svg.upload/svg)
(def icon-upload-2 hs-ui.svg.upload/svg-2)
(def icon-link hs-ui.svg.external-link/svg)
(def icon-file-check hs-ui.svg.file-check/svg)
(def icon-plus hs-ui.svg.plus/svg)
(def icon-plus-2 hs-ui.svg.plus/svg-2)
(def icon-chevron-down hs-ui.svg.chevron-down/svg)
(def icon-pin hs-ui.svg.pin/svg)
(def icon-cross hs-ui.svg.cross/svg)
(def icon-ban hs-ui.svg.ban/svg)
(def icon-trash hs-ui.svg.trash/svg)
