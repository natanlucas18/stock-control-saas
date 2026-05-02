import { useTheme } from "next-themes";
import { toast } from "sonner";

const darkThemeStyle =
  "border border-gray-200 bg-gray-900 text-white shadow-md rounded-md";
const lightThemeStyle =
  "border border-gray-200 bg-white text-gray-900 shadow-md rounded-md";

const { systemTheme } = useTheme();

export const appToast = {
  success(message: string) {
    toast.success(message, {
      className: systemTheme === "dark" ? darkThemeStyle : lightThemeStyle,
      style: {
        borderLeft: "4px solid #00ff00",
      },
    });
  },

  error(message: string) {
    toast.error(message, {
      className: systemTheme === "dark" ? darkThemeStyle : lightThemeStyle,
      style: {
        borderLeft: "4px solid #dc2626",
      },
    });
  },

  info(message: string) {
    toast(message, {
      className: systemTheme === "dark" ? darkThemeStyle : lightThemeStyle,
      style: {
        borderLeft: "4px solid #6b7280",
      },
    });
  },
};
