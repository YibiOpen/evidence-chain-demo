import defaultSettings from "@/settings";

const title = defaultSettings.title || "Electronic Evidence Chain";

export default function getPageTitle(pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${title}`;
  }
  return `${title}`;
}
