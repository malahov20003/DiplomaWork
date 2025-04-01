import { z as r, j as d, a2 as g, Q as c, R as f, ai as u, q as h } from "./copilot-C13q4MqC.js";
import { B as m } from "./base-panel-PAT65VLJ.js";
import { i as v } from "./icons-BuP5__m2.js";
const b = "copilot-features-panel{padding:var(--space-100);font:var(--font-xsmall);display:grid;grid-template-columns:auto 1fr;gap:var(--space-50);height:auto}copilot-features-panel a{display:flex;align-items:center;gap:var(--space-50);white-space:nowrap}copilot-features-panel a svg{height:12px;width:12px;min-height:12px;min-width:12px}";
var w = Object.getOwnPropertyDescriptor, $ = (e, a, t, n) => {
  for (var o = n > 1 ? void 0 : n ? w(a, t) : a, s = e.length - 1, i; s >= 0; s--)
    (i = e[s]) && (o = i(o) || o);
  return o;
};
const l = window.Vaadin.devTools;
let p = class extends m {
  render() {
    return r` <style>
        ${b}
      </style>
      ${d.featureFlags.map(
      (e) => r`
          <copilot-toggle-button
            .title="${e.title}"
            ?checked=${e.enabled}
            @on-change=${(a) => this.toggleFeatureFlag(a, e)}>
          </copilot-toggle-button>
          <a class="ahreflike" href="${e.moreInfoLink}" title="Learn more" target="_blank"
            >learn more ${v.share}</a
          >
        `
    )}`;
  }
  toggleFeatureFlag(e, a) {
    const t = e.target.checked;
    g("use-feature", { source: "toggle", enabled: t, id: a.id }), l.frontendConnection ? (l.frontendConnection.send("setFeature", { featureId: a.id, enabled: t }), c({
      type: f.INFORMATION,
      message: `“${a.title}” ${t ? "enabled" : "disabled"}`,
      details: a.requiresServerRestart ? "This feature requires a server restart" : void 0,
      dismissId: `feature${a.id}${t ? "Enabled" : "Disabled"}`
    }), u()) : l.log("error", `Unable to toggle feature ${a.title}: No server connection available`);
  }
};
p = $([
  h("copilot-features-panel")
], p);
const x = {
  header: "Features",
  expanded: !1,
  panelOrder: 35,
  panel: "right",
  floating: !1,
  tag: "copilot-features-panel",
  helpUrl: "https://vaadin.com/docs/latest/flow/configuration/feature-flags"
}, F = {
  init(e) {
    e.addPanel(x);
  }
};
window.Vaadin.copilot.plugins.push(F);
export {
  p as CopilotFeaturesPanel
};
