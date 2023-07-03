"use client";
import React from "react";
import "./globals.css";
import { Provider } from "react-redux";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { store } from "@/store/store";
import WebAccessibilityLayout from "@/component/WebAccessibility/WebAccessibilityLayout";

/**create new client */
const queryClient = new QueryClient();

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="ko">
      <Provider store={store}>
        <QueryClientProvider client={queryClient}>
          <WebAccessibilityLayout>{children}</WebAccessibilityLayout>
        </QueryClientProvider>
      </Provider>
    </html>
  );
}
